# 3D-Orientation-Detection

# Problem Statement: 
The goal of this assignment is to understand the use of sensors on the smartphone to design an app that produces the 3-D orientation of the phone when placed in your pocket. The key is to use a combination of the accelerometer, gyroscope, and compass on the phone to find the 3-D orientation of the device. 

Layout: The app uses constraint layout with TextViews, Buttons and ImageView.

# Description:
The methods implemented in MainActivity are as shown below.
onCreate(): This method retrieves specific sensors, the Accelerometer, Gyroscope and Compass and contains all the variables and buttons to get initialized.
onStop(), onDestroy(), onPause(): These methods have been used to deregister the listeners for the accelerometer. Gyroscope and Compass.
onResume(): This method will reinitialize the buttons to use once the application is resumed.
OnSensorChanged(): This method notices the sensor event and gets executed whenever there is change in any sensor, it is storing the floating-point values of accelerometer, gyroscope and compass, representing points on the x-axis, y-axis, and z-axis of the device’s coordinate system. I have used array list to store the sample values of these sensors. 
Along with these methods, I have implemented few more methods - get

# Working:
This application works better on connected android device than the emulator. I would request you to test it on connected android device.
Place your phone on table and launch the application. When the application starts, click on Collect Data button to start collecting and storing the sensor data. Once this is done, click on the Get Orientation button to get the Pitch, Roll and Yaw values. The added image will start rotating as you move your phone.  

# Check ReadMe.pdf for more details

