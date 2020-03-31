ACS CCID PC/SC Driver for Linux
Binary Packages for Linux Distributions
Advanced Card Systems Ltd.



Introduction
------------

acsccid is a PC/SC driver for Linux/Mac OS X and it supports ACS CCID smart card
readers. This library provides a PC/SC IFD handler implementation and
communicates with the readers through the PC/SC Lite resource manager (pcscd).

acsccid is based on ccid. See CCID free software driver [1] for more
information.

[1] https://ccid.apdu.fr/



System Requirements
-------------------

Debian
- jessie  (8.0)
- stretch (9.0)
- buster  (10.0)

Raspbian
- jessie  (8.0)
- stretch (9.0)
- buster  (10.0)

Ubuntu
- trusty (14.04LTS)
- xenial (16.04LTS)
- bionic (18.04LTS)
- cosmic (18.10)
- disco  (19.04)

Fedora 28/29/30

Extra Packages for Enterprise Linux (EPEL) 6/7

openSUSE Leap 42.3/15.0/15.1

SUSE Linux Enterprise (SLE) 11 SP4/12/15



Supported Readers
-----------------

CCID Readers

VID  PID  Reader              Reader Name
---- ---- ------------------- -----------------------------
072F B301 ACR32-A1            ACS ACR32 ICC Reader
072F B304 ACR3201-A1          ACS ACR3201 ICC Reader
072F B305 ACR3201             ACS ACR3201 ICC Reader
072F 8300 ACR33U-A1           ACS ACR33U-A1 3SAM ICC Reader
072F 8302 ACR33U-A2           ACS ACR33U-A2 3SAM ICC Reader
072F 8307 ACR33U-A3           ACS ACR33U-A3 3SAM ICC Reader
072F 8301 ACR33U              ACS ACR33U 4SAM ICC Reader
072F 90CC ACR38U-CCID         ACS ACR38U-CCID
072F 90CC ACR100-CCID         ACS ACR38U-CCID
072F 90D8 ACR3801             ACS ACR3801
072F B100 ACR39U              ACS ACR39U ICC Reader
072F B101 ACR39K              ACS ACR39K ICC Reader
072F B102 ACR39T              ACS ACR39T ICC Reader
072F B103 ACR39F              ACS ACR39F ICC Reader
072F B104 ACR39U-SAM          ACS ACR39U-SAM ICC Reader
072F B10C ACR39U-U1           ACS ACR39U ID1 Card Reader
072F B000 ACR3901U            ACS ACR3901 ICC Reader
072F 90D2 ACR83U-A1           ACS ACR83U
072F 8306 ACR85               ACS ACR85 PINPad Reader
072F 2011 ACR88U              ACS ACR88U
072F 8900 ACR89U-A1           ACS ACR89 ICC Reader
072F 8901 ACR89U-A2           ACS ACR89 Dual Reader
072F 8902 ACR89U-FP           ACS ACR89 FP Reader
072F 1205 ACR100I             ACS ACR100 ICC Reader
072F 1204 ACR101              ACS ACR101 ICC Reader
072F 1206 ACR102              ACS ACR102 ICC Reader
072F 2200 ACR122U             ACS ACR122U
072F 2200 ACR122U-SAM         ACS ACR122U
072F 2200 ACR122T             ACS ACR122U
072F 2214 ACR1222U-C1         ACS ACR1222 1SAM PICC Reader
072F 1280 ACR1222U-C3         ACS ACR1222 1SAM Dual Reader
072F 2207 ACR1222U-C6         ACS ACR1222 Dual Reader
072F 222B ACR1222U-C8         ACS ACR1222 1SAM PICC Reader
072F 2206 ACR1222L-D1         ACS ACR1222 3S PICC Reader
072F 222E ACR123U             ACS ACR123 3S Reader
072F 2237 ACR123U             ACS ACR123 PICC Reader
072F 2219 ACR123U Bootloader  ACS ACR123US_BL
072F 2203 ACR125              ACS ACR125 nPA plus
072F 221A ACR1251U-A1         ACS ACR1251 1S CL Reader
072F 2229 ACR1251U-A2         ACS ACR1251 CL Reader
072F 222D [OEM Reader]        [OEM Reader Name]
072F 2218 ACR1251U-C (SAM)    ACS ACR1251U-C Smart Card Reader
072F 221B ACR1251U-C          ACS ACR1251U-C Smart Card Reader
072F 2232 ACR1251UK           ACS ACR1251K Dual Reader
072F 2242 ACR1251U-C3         ACS ACR1251 1S Dual Reader
072F 2238 ACR1251U-C9         ACS ACR1251 Reader
072F 224F ACM1251U-Z2         ACS ACR1251 CL Reader
072F 223B ACR1252U-A1         ACS ACR1252 1S CL Reader
072F 223B ACR1252U-M1         ACS ACR1252 1S CL Reader
072F 223E ACR1252U-A2         ACS ACR1252 CL Reader
072F 223D ACR1252U BL         ACS ACR1252 USB FW_Upgrade v100
072F 2244 ACR1252U-A1 (PICC)  ACS ACR1252U BADANAMU MAGIC READER
072F 2259 ACR1252U-A1         ACS ACR1252IMP 1S CL Reader
072F 225B ACM1252U-Z2ACE      ACS ACR1252 CL Reader
072F 225C ACM1252U-Z2ACE BL   ACS ACR1252 USB FW_Upgrade v100
072F 223F ACR1255U-J1         ACS ACR1255U-J1 PICC Reader
072F 2239 ACR1256U            ACS ACR1256U PICC Reader
072F 2211 ACR1261U-C1         ACS ACR1261 1S Dual Reader
072F 2252 ACR1261U-A          ACS ACR1261 CL Reader
072F 2100 ACR128U             ACS ACR128U
072F 2224 ACR1281U-C1         ACS ACR1281 1S Dual Reader
072F 220F ACR1281U-C2 (qPBOC) ACS ACR1281 CL Reader
072F 2217 ACR1281U-C2 (UID)   ACS ACR1281 Dual Reader
072F 2223 ACR1281U    (qPBOC) ACS ACR1281 PICC Reader
072F 2208 ACR1281U-C3 (qPBOC) ACS ACR1281 Dual Reader
072F 0901 ACR1281U-C4 (BSI)   ACS ACR1281 PICC Reader
072F 220A ACR1281U-C5 (BSI)   ACS ACR1281 Dual Reader
072F 2215 ACR1281U-C6         ACS ACR1281 2S CL Reader
072F 2220 ACR1281U-C7         ACS ACR1281 1S PICC Reader
072F 2233 ACR1281U-K          ACS ACR1281U-K PICC Reader
072F 2234 ACR1281U-K          ACS ACR1281U-K Dual Reader
072F 2235 ACR1281U-K          ACS ACR1281U-K 1S Dual Reader
072F 2236 ACR1281U-K          ACS ACR1281U-K 4S Dual Reader
072F 2213 ACR1283L-D1         ACS ACR1283 4S CL Reader
072F 222C ACR1283L-D2         ACS ACR1283 CL Reader
072F 220C ACR1283 Bootloader  ACS ACR1283U FW Upgrade
072F 2258 ACR1311U-N1         ACS ACR1311 PICC Reader
072F 0102 AET62               ACS AET62 PICC Reader
072F 0103 AET62               ACS AET62 1SAM PICC Reader
072F 0100 AET65               ACS AET65 ICC Reader
072F 224A AMR220-C            ACS AMR220 Reader
072F 8201 APG8201-A1          ACS APG8201
072F 8206 APG8201-B2          ACS APG8201-B2
072F 8202 [OEM Reader]        [OEM Reader Name]
072F 8205 [OEM Reader]        [OEM Reader Name]
072F 90DB CryptoMate64        ACS CryptoMate64
072F B200 ACOS5T1             ACS CryptoMate (T1)
072F B106 ACOS5T2             ACS CryptoMate (T2)
072F B112 ACOS5T2             ACS CryptoMate EVO

non-CCID Readers

VID  PID  Reader              Reader Name
---- ---- ------------------- -----------------------------
072F 9000 ACR38U              ACS ACR38U
072F 90CF ACR38U-SAM          ACS ACR38U-SAM
072F 90CE [OEM Reader]        [OEM Reader Name]
072F 0101 AET65               ACS AET65 1SAM ICC Reader
072F 9006 CryptoMate          ACS CryptoMate



Installation
------------

1. Before installing the driver, unplug the reader first and make sure that you
   have installed pcsc-lite package for your Linux distribution.

2. Login as root. If you are already login as normal user, enter "su" command to
   become administrator. In Ubuntu, you can use "sudo" command with package
   installation.

3. To install the driver, enter the following commands:

Debian/Raspbian/Ubuntu
# dpkg -i [package filename]

Fedora/EPEL/openSUSE/SLE
# rpm -ivh [package filename]

4. Restart pcscd service. If the pcscd service cannot be restarted, reboot the
   system.

# /etc/init.d/pcscd restart

5. Plug the reader to the system. You can list the readers from your PC/SC
   application.

6. To uninstall the driver, enter the following commands:

Debian/Raspbian/Ubuntu
# dpkg -r [package name]

Fedora/EPEL/openSUSE/SLE
# rpm -e [package name]



History
-------

v1.1.7 (25/7/2019)
- Add binary packages for Debian 8.0/9.0/10.0, Raspbian 8.0/9.0/10.0,
  Ubuntu 14.04LTS/16.04LTS/18.04LTS/18.10/19.04, Fedora 28/29/30,
  Extra Packages for Enterprise Linux (EPEL) 6/7, openSUSE Leap 42.3/15.0/15.1
  and SUSE Linux Enterprise (SLE) 11 SP4/12/15.

v1.1.6 (30/10/2018)
- Add binary packages for Debian 8.0/9.0, Raspbian 8.0/9.0,
  Ubuntu 14.04LTS/16.04LTS/18.04LTS/18.10, Fedora 27/28/29,
  Extra Packages for Enterprise Linux (EPEL) 6/7, openSUSE Leap 42.3/15.0 and
  SUSE Linux Enterprise (SLE) 11 SP4/12/15.

v1.1.5 (27/10/2017)
- Add binary packages for Debian 7.0/8.0/9.0, Raspbian 7.0/8.0/9.0,
  Ubuntu 14.04LTS/16.04LTS/17.04/17.10, Fedora 25/26/27,
  Extra Packages for Enterprise Linux (EPEL) 7 and openSUSE Leap 42.2/Leap 42.3.

v1.1.4 (16/12/2016)
- Add binary packages for Debian 7.0/8.0, Raspbian 7.0/8.0,
  Ubuntu 12.04LTS/14.04LTS/16.04LTS/16.10, Fedora 23/24/25,
  Extra Packages for Enterprise Linux (EPEL) 7 and
  openSUSE 13.2/Leap 42.1/Leap 42.2.

v1.1.3 (24/6/2016)
- Add binary packages for Debian 7.0/8.0, Raspbian 7.0/8.0,
  Ubuntu 12.04LTS/14.04LTS/15.10/16.04LTS, Fedora 22/23/24,
  Extra Packages for Enterprise Linux (EPEL) 7 and openSUSE 13.1/13.2/Leap 42.1.

v1.1.2 (19/2/2016)
- Add binary packages for Debian 7.0/8.0, Raspbian 7.0/8.0,
  Ubuntu 14.04LTS/15.04/15.10, Fedora 21/22/23,
  Extra Packages for Enterprise Linux (EPEL) 7 and openSUSE 13.1/13.2/Leap 42.1.

v1.1.1 (9/11/2015)
- Add binary packages for Debian 7.0/8.0, Raspbian 7.0/8.0,
  Ubuntu 14.04LTS/15.04/15.10, Fedora 21/22/23,
  Extra Packages for Enterprise Linux (EPEL) 7 and openSUSE 13.1/13.2/Leap 42.1.

v1.1.0 (15/12/2014)
- Add binary packages for Debian 7.0, Ubuntu 14.04LTS/14.10, Fedora 19/20/21,
  Extra Packages for Enterprise Linux (EPEL) 7 and openSUSE 12.3/13.1/13.2.

v1.0.8 (4/7/2014)
- Add binary packages for Debian 6.0/7.0,
  Ubuntu 10.04LTS/12.04LTS/13.10/14.04LTS, Fedora 19/20,
  Extra Packages for Enterprise Linux (EPEL) 5/6 and openSUSE 12.3/13.1.

v1.0.7 (17/6/2014)
- Add binary packages for Debian 6.0/7.0,
  Ubuntu 10.04LTS/12.04LTS/13.10/14.04LTS, Fedora 19/20,
  Extra Packages for Enterprise Linux (EPEL) 5/6 and openSUSE 12.3/13.1.

v1.0.6 (24/4/2014)
- Add binary packages for Debian 6.0/7.0,
  Ubuntu 10.04LTS/12.04LTS/13.10/14.04LTS, Fedora 19/20,
  Extra Packages for Enterprise Linux (EPEL) 5/6 and openSUSE 12.3/13.1.

v1.0.5 (11/9/2013)
- Add binary packages for Debian 6.0/7.0, Ubuntu 10.04LTS/12.04LTS/12.10/13.04,
  Fedora 18/19 and openSUSE 12.2/12.3.

v1.0.4 (10/7/2012)
- Add binary packages for Debian 6.0/wheezy,
  Ubuntu 8.04LTS/10.04LTS/11.04/11.10/12.04LTS/quantal, Fedora 14/15/16/17 and
  openSUSE 11.4/12.1.

v1.0.3 (17/2/2012)
- Add binary packages for Fedora 14 - 16.

v1.0.3 (7/2/2012)
- Add binary packages for openSUSE 11.1 - 12.1.

v1.0.3 (6/2/2012)
- Add binary packages for Debian 5.0/6.0 and Ubuntu 8.04 - 11.10.

v1.0.2 (16/3/2011)
- Add binary packages for Debian 5.0, Ubuntu 10.10, Fedora 14 and openSUSE 11.3.

v1.0.2 (29/11/2010)
- Add binary packages for Ubuntu 9.10/10.10 and openSUSE 11.3.

v1.0.2 (24/11/2010)
- Update driver to v1.0.2.
- Add binary packages for Debian 5.0, Ubuntu 9.04/10.04 and openSUSE 11.1/11.2.

v1.0.2 (17/6/2010)
- Update driver to v1.0.2.
- Update source and binary packages for Fedora 13, openSUSE 11.2 and Debian 5.0.

v1.0.1 (9/11/2009)
- Update driver to v1.0.1.

v1.0.0 (9/9/2009)
- New release.
- Based on ccid-1.3.11 (http://pcsclite.alioth.debian.org/ccid.html).
- Add source and binary packages for Fedora 11, openSUSE 11.1 and Debian 5.0.



File Contents
-------------

|   ReadMe.txt
|
+---debian
|   +---buster
|   |       libacsccid1_1.1.7-1~bpo10+1_amd64.deb
|   |       libacsccid1_1.1.7-1~bpo10+1_i386.deb
|   |
|   +---jessie
|   |       libacsccid1_1.1.7-1~bpo8+1_amd64.deb
|   |       libacsccid1_1.1.7-1~bpo8+1_i386.deb
|   |
|   \---stretch
|           libacsccid1_1.1.7-1~bpo9+1_amd64.deb
|           libacsccid1_1.1.7-1~bpo9+1_i386.deb
|
+---epel
|   +---6
|   |       pcsc-lite-acsccid-1.1.7-1.el6.i686.rpm
|   |       pcsc-lite-acsccid-1.1.7-1.el6.x86_64.rpm
|   |
|   \---7
|           pcsc-lite-acsccid-1.1.7-1.el7.x86_64.rpm
|
+---fedora
|   +---28
|   |       pcsc-lite-acsccid-1.1.7-1.fc28.i686.rpm
|   |       pcsc-lite-acsccid-1.1.7-1.fc28.x86_64.rpm
|   |
|   +---29
|   |       pcsc-lite-acsccid-1.1.7-1.fc29.i686.rpm
|   |       pcsc-lite-acsccid-1.1.7-1.fc29.x86_64.rpm
|   |
|   \---30
|           pcsc-lite-acsccid-1.1.7-1.fc30.i686.rpm
|           pcsc-lite-acsccid-1.1.7-1.fc30.x86_64.rpm
|
+---opensuse
|   +---leap15.0
|   |       pcsc-acsccid-1.1.7-lp150.78.1.x86_64.rpm
|   |
|   +---leap15.1
|   |       pcsc-acsccid-1.1.7-lp151.78.1.x86_64.rpm
|   |
|   \---leap42.3
|           pcsc-acsccid-1.1.7-78.1.x86_64.rpm
|
+---raspbian
|   +---buster
|   |       libacsccid1_1.1.7-1~bpo10+1_armhf.deb
|   |
|   +---jessie
|   |       libacsccid1_1.1.7-1~bpo8+1_armhf.deb
|   |
|   \---stretch
|           libacsccid1_1.1.7-1~bpo9+1_armhf.deb
|
+---sle
|   +---11sp4
|   |       pcsc-acsccid-1.1.7-78.1.i586.rpm
|   |       pcsc-acsccid-1.1.7-78.1.x86_64.rpm
|   |
|   +---12
|   |       pcsc-acsccid-1.1.7-78.1.x86_64.rpm
|   |
|   \---15
|           pcsc-acsccid-1.1.7-78.1.x86_64.rpm
|
\---ubuntu
    +---bionic
    |       libacsccid1_1.1.7-1~ubuntu18.04.1_amd64.deb
    |       libacsccid1_1.1.7-1~ubuntu18.04.1_i386.deb
    |
    +---cosmic
    |       libacsccid1_1.1.7-1~ubuntu18.10.1_amd64.deb
    |       libacsccid1_1.1.7-1~ubuntu18.10.1_i386.deb
    |
    +---disco
    |       libacsccid1_1.1.7-1~ubuntu19.04.1_amd64.deb
    |       libacsccid1_1.1.7-1~ubuntu19.04.1_i386.deb
    |
    +---trusty
    |       libacsccid1_1.1.7-1~ubuntu14.04.1_amd64.deb
    |       libacsccid1_1.1.7-1~ubuntu14.04.1_i386.deb
    |
    \---xenial
            libacsccid1_1.1.7-1~ubuntu16.04.1_amd64.deb
            libacsccid1_1.1.7-1~ubuntu16.04.1_i386.deb



Support
-------

In case of problem, please contact ACS through:

Web Site: http://www.acs.com.hk/
E-mail: info@acs.com.hk
Tel: +852 2796 7873
Fax: +852 2796 1286



-------------------------------------------------------------------------------
Copyright (C) 2009-2019 Advanced Card Systems Ltd.
Copyright (C) 2003-2011 Ludovic Rousseau
Copyright (C) 2000-2001 Carlos Prados
Copyright (C) 2003 Olaf Kirch
Copyright (C) 1999-2002 Matthias Bruestle

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301  USA
