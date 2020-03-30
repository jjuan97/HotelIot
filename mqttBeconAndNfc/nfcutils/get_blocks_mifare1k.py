from smartcard.scard import SCardEstablishContext, SCardListReaders
from smartcard.scard import SCardGetStatusChange, SCardConnect, SCardTransmit
from smartcard.scard import SCARD_SCOPE_USER, SCARD_STATE_UNAWARE
from smartcard.scard import SCARD_STATE_PRESENT, SCARD_SHARE_SHARED
from smartcard.scard import SCARD_PROTOCOL_T0, SCARD_PROTOCOL_T1
import smartcard.System
import smartcard.util

start_page = 0
pages = 1
adpu_auth_command = [
    0xFF,0x82,0x00,0x00,0x06,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF #auth only mifare 1k/4k
]
adpu_auth_block_command = [
    0xFF,0x88,0x00,0x04,0x60,0x00 #auth blocks only mifare 1k/4k
]

#get page x command [0xFF,0xB0,0x00, hex(x_page) ,0x10]
adpu_get_list = []
for x in range(start_page, start_page + pages):
    adpu_get_command = [0xFF,0xCA,0x00]
    adpu_get_command.append(x)
    adpu_get_command.append(0x00)
        
    adpu_get = {}
    adpu_get["page"] = x
    adpu_get["command"] = adpu_get_command
    adpu_get_list.append(adpu_get)

hresult, hcontext = SCardEstablishContext(SCARD_SCOPE_USER)
hresult, readers = SCardListReaders(hcontext, [])
readerstates = []
for i in range(len(readers)):
    readerstates += [(readers[i], SCARD_STATE_UNAWARE)]

hresult, newstates = SCardGetStatusChange(hcontext, 0, readerstates)
for reader, eventstate, atr in newstates:
    if eventstate & SCARD_STATE_PRESENT:
        hresult, hcard, dwActiveProtocol = SCardConnect(
            hcontext,
            reader,
            SCARD_SHARE_SHARED,
            SCARD_PROTOCOL_T0 | SCARD_PROTOCOL_T1)

        #SCardTransmit(hcard, dwActiveProtocol, adpu_auth_command)
        #SCardTransmit(hcard, dwActiveProtocol, adpu_auth_block_command)

        for adpu_get in adpu_get_list:
            hresult, response = SCardTransmit(hcard, dwActiveProtocol, adpu_get["command"])
            code_response = smartcard.util.toHexString(response[-2:])
            filter_response = response[:-2]
            string_response = smartcard.util.HexListToBinString(filter_response)
            hexstring_response = smartcard.util.toHexString(filter_response)
            if code_response == "90 00":
                print "page" + str(adpu_get["page"]) + ": " + hexstring_response + " => " +  string_response
            else:
                print "page" + str(adpu_get["page"]) + ": error"
            print ""
