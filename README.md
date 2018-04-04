
React Native Snowboy hotword detection engine.


## Purpose 

A hotword detection engine for Android.

## Setup

* Install Module

```bash
npm install --save react-native-snowboy
```

* In `android/settings.gradle`

```gradle
...
include ':react-native-snowboy'
project(':react-native-snowboy').projectDir = new File(settingsDir, '../node_modules/react-native-snowboy')
```

* In `android/app/build.gradle`

```gradle
dependencies {
	...
	compile project(':react-native-snowboy')
}
```

* In MainApplication.java

```java
...
import a.kitt.snowboy.*; // <-------- Add this to import package

public class MainApplication extends Activity implements ReactApplication {
...
	
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        new RNSnowBoyPackage() // <------ Add this! 
        );
    }
}
```
* In YourProject/android/app/src/main/assets

Create **snowboy** folder and put inside it the files **alexa.umdl** (or your own *umdl* or *umdl* file) and **common.res**.
You can get these files [here](https://github.com/Kitt-AI/snowboy/tree/master/examples/Android/SnowboyAlexaDemo/assets/snowboy) 
https://github.com/Kitt-AI/snowboy/tree/master/examples/Android/SnowboyAlexaDemo/assets/snowboy

## Usage

Currently there following functionality available.

- initHotword
- startRecording
- stopRecording
- destroy


### Importing module


```js
let snowboy = require('react-native-snowboy')

```

### initHotword()
Init the engine.

## Example 

```js
componentDidMount() {
	snowboy.initHotword()
	.then((res)=> {
		console.log(res)
	})
	.catch((err)=> {
		console.log(err)
	})
}

```

### startRecording() and stopRecording()

## Example

```js
toggle = () => {
	if(this.state.isRecording) {
		snowboy.stopRecording();
		this.setState({event:'not recording...'})
	} else {
		snowboy.startRecording();
		this.setState({event:'recording...'})
	}

	this.setState({
		isRecording: !this.state.isRecording
	})
}

```

### destroy()

Destroy the engine.

## Example

```js
componentWillUnmount() {
	snowboy.destroy();
}

```

### Events

Use event listeners to do whatever you want when the hotword is detected

## Example

```js
componentWillMount() {	
	// No hotword detected
	snowboy.addEventListener("msg-vad-speech", (e) => {
		console.log('msg-vad-speech',e)
	})
	
	// No speech: silence
	snowboy.addEventListener("msg-vad-nospeech", (e) => {
		console.log('msg-vad-nospeech',e)
	})
	
	// The hotword is decteced
	snowboy.addEventListener("msg-active", (e) => {
		console.log('msg-active',e)
	})
}

```


## LICENSE

MIT
