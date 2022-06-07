# DualProgressView

![ProgressView Demo](https://raw.githubusercontent.com/pollux-/DualProgressBar/master/raw/progress.gif)

#### How to start ?

```kotlin
 // calling with default config
DualProgressView() 
```

#### Want more ?

```kotlin
@Composable
fun DualProgressView(
    modifier: Modifier = Modifier,
    radius: Int = 100,
    innerCircleColor: Color = MaterialTheme.colors.primary,
    outerCircleColor: Color = MaterialTheme.colors.secondary,
    strokeWidth: Dp = 4.dp,
)


```

If you want to know in detail about this animation, read it
here https://medium.com/@sreekumar_av/how-to-create-your-own-progressbar-in-android-511419293158


License
=======

    Copyright 2022 Google, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
