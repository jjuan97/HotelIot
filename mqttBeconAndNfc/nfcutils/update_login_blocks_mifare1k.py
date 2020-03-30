from smartcard.scard import SCardEstablishContext, SCardListReaders
from smartcard.scard import SCardGetStatusChange, SCardConnect, SCardTransmit
from smartcard.scard import SCARD_SCOPE_USER, SCARD_STATE_UNAWARE
from smartcard.scard import SCARD_STATE_PRESENT, SCARD_SHARE_SHARED
from smartcard.scard import SCARD_PROTOCOL_T0, SCARD_PROTOCOL_T1
import smartcard.System
import smartcard.util

#max 15 characters
name = "ayudante"
domain = "@husj.com"
password = "estudiante1"
separator = "/"

adpu_auth_command = [
    0xFF,0x82,0x00,0x00,0x06,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF #auth only mifare 1k/4k
]
adpu_auth_block_command = [
    0xFF,0x88,0x00,0x04,0x60,0x00 #auth blocks only mifare 1k/4k
]

adpu_update_page4_command = [0xFF,0xD6,0x00,0x04,0x10] + smartcard.util.BinStringToHexList(name + separator)
adpu_update_page5_command = [0xFF,0xD6,0x00,0x05,0x10] + smartcard.util.BinStringToHexList(domain + separator)
adpu_update_page6_command = [0xFF,0xD6,0x00,0x06,0x10] + smartcard.util.BinStringToHexList(password + separator)

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

        try:
            SCardTransmit(hcard, dwActiveProtocol, adpu_auth_command)
            SCardTransmit(hcard, dwActiveProtocol, adpu_auth_block_command)

            hresult, response_page4 = SCardTransmit(hcard, dwActiveProtocol, adpu_update_page4_command)
            hresult, response_page5 = SCardTransmit(hcard, dwActiveProtocol, adpu_update_page5_command)
            hresult, response_page6 = SCardTransmit(hcard, dwActiveProtocol, adpu_update_page6_command)
            
            filter_response_page4 = response_page4[-2:]
            filter_response_page5 = response_page5[-2:]
            filter_response_page6 = response_page6[-2:]

            stringResponse_page4 = smartcard.util.toHexString(filter_response_page4)
            stringResponse_page5 = smartcard.util.toHexString(filter_response_page5)
            stringResponse_page6 = smartcard.util.toHexString(filter_response_page6)
            
            page4_print = "ok" if stringResponse_page4 == "90 00" else "error"
            page5_print = "ok" if stringResponse_page5 == "90 00" else "error"
            page6_print = "ok" if stringResponse_page6 == "90 00" else "error"

            print page4_print + " page 4"
            print page5_print + " page 5"
            print page6_print + " page 6"
        except:
            print "error"
