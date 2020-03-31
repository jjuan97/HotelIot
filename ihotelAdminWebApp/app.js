window.addEventListener('load', start, false);

async function start() {

    // Listar Habitaciones diponibles
    db.collection("habitaciones").where("ocupado", "==", false).get().
        then((querySnapshot) => {

            querySnapshot.forEach((doc) => {
                //document.getElementById("habitaciones").insertAdjacentHTML('beforeend','<option value="'+ doc.id +'">'+ doc.data().numero + " - "+ doc.data().tipo +'</option>');
                //console.log(doc.id, "\n", doc.data());
                $("#habitaciones").append('<option value="' + doc.id + '">' + doc.data().numero + " - " + doc.data().tipo + '</option>');
            });
            $('#habitaciones').formSelect();

        }).catch(function (error) {
            console.log("Error getting documents: ", error);
        });


    // Listar dispositivos NFC
    db.collection("NFC").where("ocupado", "==", false).get().
        then((querySnapshot) => {

            querySnapshot.forEach((doc) => {
                //console.log(doc.id, "\n", doc.data());
                $("#nfc").append('<option value="' + doc.id + '">' + doc.data().uid + '</option>');
            });
            $('#nfc').formSelect();

        }).catch(function (error) {
            console.log("Error getting documents: ", error);
        });

    // Listar Beacons
    db.collection("beacon").where("ocupado", "==", false).get().
        then((querySnapshot) => {
            querySnapshot.forEach((doc) => {
                //console.log(doc.id, "\n", doc.data());
                $("#beacon").append('<option value="' + doc.id + '">' + doc.data().uid + '</option>');
            });
            $('#beacon').formSelect();

        }).catch(function (error) {
            console.log("Error getting documents: ", error);
        });


    // Listar usuarios
    db.collection("usuarios").get().
        then((querySnapshot) => {
            querySnapshot.forEach((doc) => {
                var checked = "unchecked";
                if (doc.data().activo == true) {
                    checked = "checked"
                    var html = '<tr>' +
                        '<td name="email">' + doc.data().email + '</td>' +
                        '<td name="cc">' + doc.data().cedula + '</td>' +
                        '<td name="habitacion">' + doc.data().habitacion + '</td>' +
                        '<td name="nfcUID">' + doc.data().nfcUID + '</td>' +
                        '<td name="beaconUID">' + doc.data().beaconUID + '</td>' +
                        '<td name="eliminar">' +
                        '<a class="re daken-3 waves-effect waves-light btn modal-trigger" href="#confirmar" data-id="' + doc.id + '">' +
                        '<i class="material-icons center">delete</i>' +
                        '</a>' +
                        '</td>' +
                        '</tr>';
                    $("#usuariosActivos").append(html);
                }

            });
            eventoClickEliminar();

        }).catch(function (error) {
            console.log("Error getting documents: ", error);
        });
}


function registroAuth(email, cc) {

    return new Promise(resultado => {
        firebase.auth().createUserWithEmailAndPassword(email, cc)
            .then(function () {
                resultado("Success");
            })
            .catch(function (error) {
                // Handle Errors here.
                var errorCode = error.code;
                var errorMessage = error.message;
                console.log(errorMessage);
                resultado(errorMessage);
            });
    });
    //var resultado = "";
    /*
    firebase.auth().createUserWithEmailAndPassword(email, cc)
    .then(function(){
        resultado = "Success";
    })
    .catch(function (error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;
        console.log(errorMessage);
        resultado = String(errorMessage);
    });    
   //console.log(email, cc);    
   return resultado;
   */
}

function registroFirestore(nombre, email, cc, creditos, nfcId, beaconId, habitacionId) {
    var data = {
        nombre: nombre,
        email: email,
        cedula: cc,
        creditos: creditos,
        nfcUID: nfcId,
        beaconUID: beaconId,
        habitacion: habitacionId,
        activo: true
    };
    //console.log(data);

    db.collection("usuarios").add(data)
        .then(function (docRef) {
            console.log("Document written with ID: ", docRef.id);
        })
        .catch(function (error) {
            console.error("Error adding document: ", error);
        });

}

async function actualizarEstados(nfcId, beaconId, habitacionId, ocupado) {

    var done = await db.collection("NFC").doc(nfcId).update({
        ocupado: ocupado
        })
        .then(function () {
            console.log("NFC successfully updated!");
            return true;
        })
        .catch(function (error) {
            // The document probably doesn't exist.
            console.error("Error updating NFC: ", error);
            return false;
        });


    var done2 = await db.collection("beacon").doc(beaconId).update({
        ocupado: ocupado
        })
        .then(function () {
            console.log("Beacon successfully updated!");
            return true;
        })
        .catch(function (error) {
            console.error("Error updating Beacon: ", error);
            return false;
        });


    var done3 = await db.collection("habitaciones").doc(habitacionId).update({
        ocupado: ocupado
        })
        .then(function () {
            console.log("Room successfully updated!");
            return true;
        })
        .catch(function (error) {
            console.error("Error updating Room: ", error);
            return false;
        });
    return done && done2 && done3;
}

// handler Registrar
$("#registro").submit(async function (event) {
    event.preventDefault();
    M.toast({
        html: 'Guardando ...',
        inDuration: 300,
        displayLength: 2000,
        outDuration: 300,
        classes: "teal rounded"
    });

    var nombre = $("#nombre").val();
    var email = $("#email").val();
    var cc = $("#cc").val();
    var creditos = $("#creditos").val();
    var nfcId = $("#nfc").val();  // no usada
    var nfcUID = $('select[name="nfc"] option:selected').text();
    var beaconId = $("#beacon").val();  // no usada
    var beaconUID = $('select[name="beacon"] option:selected').text();
    var habitacionId = $("#habitaciones").val();
    var habitacionNumero = $('select[name="habitacion"] option:selected').text();

    var resultado = await registroAuth(email, cc);

    console.log(resultado);

    if (resultado != "Success") {
        M.Toast.dismissAll();
        M.toast({
            html: resultado,
            inDuration: 300,
            displayLength: 8000,
            outDuration: 300,
            classes: "red darken-3 rounded"
        });
    }
    else {
        registroFirestore(nombre, email, cc, creditos, nfcUID, beaconUID, habitacionNumero);
        var done = await actualizarEstados(nfcId, beaconId, habitacionId, true);

        M.Toast.dismissAll();
        M.toast({
            html: 'Usuario Registrado Correctamente',
            inDuration: 100,
            outDuration: 300,
            displayLength: 5000,
            classes: "teal rounded",
        });
        if(done)
            location.reload();
    }
});


function obtenerId(coleccion, attributo, valor){
    return db.collection(coleccion).where(attributo, "==", valor)
    .get()
    .then(function(querySnapshot) {
        var id;
        querySnapshot.forEach(function(doc) {
            // doc.data() is never undefined for query doc snapshots
            //console.log(doc.id, " => ", doc.data());
            id = doc.id;
        });
        return id;
    })
    .catch(function(error) {
        console.log("Error getting documents: ", error);
        return null;
    });    
}

function eventoClickEliminar() {
    $('#usuariosActivos td[name="eliminar"] .btn').on("click", function () {
        let row = $(this).parent().parent();
        let email = row.find('td[name="email"]').text();
        let habitacion = row.find('td[name="habitacion"]').text().split(" ")[0];
        let nfcUID = row.find('td[name="nfcUID"]').text();
        let beaconUID = row.find('td[name="beaconUID"]').text();
        let id = $(this).data('id');
        //console.log("eliminando..."+id);
        //console.log(habitacion, nfcUID, beaconUID);

        $('.modal').modal();
        $('#confirmar .modal-footer .btn').on("click", async function () {
            var cc = $('#cedulaConfirm').val();
            M.Toast.dismissAll();
            M.toast({
                html: 'Eliminando...',
                inDuration: 300,
                displayLength: 5000,
                outDuration: 300,
                classes: "teal rounded"
            });
            var resultadoLogin = await firebase.auth().signInWithEmailAndPassword(email, cc).catch(function (error) {
                // Handle Errors here.
                var errorCode = error.code;
                var errorMessage = error.message;
                M.Toast.dismissAll();
                M.toast({
                    html: 'Error : ' + errorMessage,
                    inDuration: 300,
                    displayLength: 8000,
                    outDuration: 300,
                    classes: "red darken-3 rounded"
                });
            });

            if (resultadoLogin != null) {
                console.log("Loggeado", "=>", resultadoLogin.user);

                firebase.auth().currentUser
                .delete().then(async function () {
                    M.Toast.dismissAll();
                    M.toast({
                        html: 'Usuario eliminado!',
                        inDuration: 300,
                        displayLength: 3000,
                        outDuration: 300,
                        classes: "teal rounded"
                    });
                    var nfcId = await obtenerId("NFC", "uid", nfcUID);
                    console.log(nfcId);
                    var beaconId = await obtenerId("beacon", "uid", beaconUID);
                    console.log(beaconId);
                    var habitacionId = await obtenerId("habitaciones", "numero", Number(habitacion));
                    console.log(habitacionId);

                    db.collection("usuarios").doc(id).delete().then(function() {
                        console.log("User successfully deleted!");
                    }).catch(function(error) {
                        console.error("Error removing User: ", error);
                    });
                    var done = await actualizarEstados(nfcId, beaconId, habitacionId, false);

                    if(done)
                        location.reload();

                }).catch(function (error) {
                    M.Toast.dismissAll();
                    M.toast({
                        html: 'Error: ' + error.errorMessage,
                        inDuration: 300,
                        displayLength: 5000,
                        outDuration: 300,
                        classes: "red darken-3 rounded"
                    });
                });                
            }
        });
    });
}
