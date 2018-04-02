var DeviceEventEmitter = require('react-native').DeviceEventEmitter; 
var SnowBoy = require('react-native').NativeModules.SnowBoy;
var RNSnowBoy = {
	startRecording() {
		SnowBoy.start();
	},
	stopRecording() {
		SnowBoy.stop();
	},
	initHotword() {
		SnowBoy.initHotword();
	},
	destroy() {
		SnowBoy.destroy();
	}
}
module.exports = RNSnowBoy;
