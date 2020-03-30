import rfidiot

print 'Inicio'
try:
    card= rfidiot.card
    args= rfidiot.args
    if len(args) == 1:
        card.settagtype(args[0])
    else:
        card.settagtype(card.ALL)
    if card.select():
        print 'Tag ID: ' + card.uid
except:
    pass
finally:
    print 'Fin'
