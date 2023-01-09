# 3D-Orientation-Detection

Problem Statement: 
The goal of this assignment is to understand the use of sensors on the smartphone to design an app that produces the 3-D orientation of the phone when placed in your pocket. The key is to use a combination of the accelerometer, gyroscope, and compass on the phone to find the 3-D orientation of the device. 

Layout: The app uses constraint layout with TextViews, Buttons and ImageView.

Description:
The methods implemented in MainActivity are as shown below.
onCreate(): This method retrieves specific sensors, the Accelerometer, Gyroscope and Compass and contains all the variables and buttons to get initialized.
onStop(), onDestroy(), onPause(): These methods have been used to deregister the listeners for the accelerometer. Gyroscope and Compass.
onResume(): This method will reinitialize the buttons to use once the application is resumed.
OnSensorChanged(): This method notices the sensor event and gets executed whenever there is change in any sensor, it is storing the floating-point values of accelerometer, gyroscope and compass, representing points on the x-axis, y-axis, and z-axis of the device’s coordinate system. I have used array list to store the sample values of these sensors. 
Along with these methods, I have implemented few more methods - get

Algorithm:
The sensor data is collected in array lists named as listacc, listgyro, listcomp. To get the fused values of pitch and roll, I have used the accelerometer and gyroscope values of pitch and roll. For the yaw value, I have fused accelerometer and compass to calculate it and later I have fused it will gyroscope yaw value.
In OnSensorChanged method, I am calculating the values of pitch, roll and yaw for accelerometer, gyroscope and compass. The gyroscope data is integrated every timestep with the current angle value. For that I have used the formulas and converted the angles into degrees.
For accelerometer I have used the following formula to calculate the angle.
 
For gyroscope following formula is used as shown below:
 
gyroPitch = gyroPitch + ((−𝑥𝑔) × ∆𝑡 × (180/3.14)) % 360
gyroRoll = gyroRoll + ((𝑦𝑔) × ∆𝑡 × (180/3.14)) % 360
gyroYaw= gyroYaw + ((−𝑧𝑔) × ∆𝑡 × (180/3.14)) % 360
Here, the % 360 degree is used to correctly rotate the image, otherwise we can take 90 degrees as well.
I have also added a complimentary filter to fuse the values of pitch, roll and yaw from accelerometer, gyroscope, and compass. To get the "best of both worlds". We use the data from the gyroscope in the near term since it is very precise and not affected by external influences. We use the data from the accelerometer in the long run because it does not wander. As a result, the gyroscope is used 98 percent of the time while the accelerometer is used only 2% of the time and we get the final values as shown below:
fpitch = (gyroPitch × 0.98 ) + (accPitch× 0.02 )
froll= (gyroRoll × 0.98 ) + (accRoll × 0.02 )
fyaw = (gyroYaw× 0.98 ) + (accYaw× 0.02 )
The image of hand holding the phone will rotate according to these final values.

Working:
This application works better on connected android device than the emulator. I would request you to test it on connected android device.
Place your phone on table and launch the application. When the application starts, click on Collect Data button to start collecting and storing the sensor data. Once this is done, click on the Get Orientation button to get the Pitch, Roll and Yaw values. The added image will start rotating as you move your phone.



UI:
   

Limitation:
The device needed for testing should be of Android version 8 or greater and there is some error in the final values due to the noise in the data received from the sensors. I was not able to fix the error. I’ll try to resolve the error in next version.

References:
https://www.youtube.com/watch?v=p7tjtLkIlFo
https://stackoverflow.com/questions/14513597/cannot-convert-from-double-to-float
https://math.stackexchange.com/questions/2466949/get-magnetic-field-values-from-euler-angle
https://arxiv.org/pdf/1704.06053.pdf
https://www.youtube.com/watch?v=T9jXoG0QYIA
https://www.youtube.com/watch?v=0rlvvYgmTvI
https://arduino.stackexchange.com/questions/53478/what-is-corrrect-the-way-to-find-roll-pitch-yaw
https://engineering.stackexchange.com/questions/3348/calculating-pitch-yaw-and-roll-from-mag-acc-and-gyro-data
https://students.iitk.ac.in/roboclub/2017/12/21/Beginners-Guide-to-IMU.html


