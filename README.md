# DualProgressView

[ ![Download](https://api.bintray.com/packages/sreekumar/Artifact/dualprogress/images/download.svg) ](https://bintray.com/sreekumar/Artifact/dualprogress/_latestVersion)

![ProgressView Demo](https://raw.githubusercontent.com/pollux-/DualProgressBar/master/raw/progress.gif)	

#### How to use

Add below to your root `build.gradle` file

```java
allprojects {
    repositories {
        google()
        jcenter()
    }
}
```
then in your module dependency 

```java
 implementation 'com.pollux:dualprogressview:0.0.1'
```

```xml
<com.pollux.widget.DualProgressView
       android:layout_width="32dp"
       android:layout_height="32dp"/>
```

#### Want more ?

```xml
<com.pollux.widget.DualProgressView
       android:layout_width="32dp"
       android:layout_height="32dp"
       app:dpv_inner_color="@color/colorPrimaryDark"
       app:dpv_inner_padding="8dp"
       app:dpv_outer_color="@color/colorAccent"
       app:dpv_thickness="2dp" />

```
If you want to know in detail about this animation, read it here https://medium.com/@sreekumar_av/how-to-create-your-own-progressbar-in-android-511419293158


License
=======

    Copyright 2018 Google, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
