
First of all, original code is from 2016, using some weird techniques I never have seen at a 
at a customers place ever: dropwizard and guice.

I did not update it that much.

--- How to get a ZUMO robot working. ---

This project requires a zumo robot kit with a motor control circuit and some wiring harnesses:
https://www.pololu.com/category/129/zumo-robots-and-accessories
You will need chassis, 2x motors (I used 75 version), and a DRV8833 chip.
https://www.pololu.com/product/2130
Wiring stuff I got from kjell.com.

Regarding the raspberry...

* Checkout Circuit pinout here: https://pi4j.com/getting-started/understanding-the-pins/
* You need to install wiringpi, build from sources:
```
   $  sudo apt-get --yes install git-core gcc make
   $  cd ~
   $  git clone https://github.com/WiringPi/WiringPi --branch master --single-branch wiringpi
   $  cd ~/wiringpi
   $  sudo ./build
```

How to get PS4 controller working:

* Check here: https://salamwaddah.com/blog/connecting-ps4-controller-to-raspberry-pi-via-bluetooth
* Then you need to enable kernel loading of BT stack for it to be stabile, /boot/firmware/config.txt -> dtconfig=krnbt
  https://github.com/RPi-Distro/pi-bluetooth/issues/2

Install this on your rpi, with a java -cp zumo.jar server zumo.yaml start command
It will die if it does not find any Joysticks. 

This is the service file for linux:

```Unit]
Description=zumo
Wants=bluetooth.target
After=bluetooth.target

[Service]
User=root
WorkingDirectory=/home/ubuntu/zumo
ExecStart=java -jar zumo.driver-1.0-SNAPSHOT.jar server zumo.yml
TimeoutStopSec=10
Restart=on-failure
RestartSec=60

[Install]
WantedBy=multi-user.target
```