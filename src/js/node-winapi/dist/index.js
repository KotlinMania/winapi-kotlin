// Entry point for winapi N-API native module
// Shared between Kotlin/JS (jsMain) and Kotlin/WASM (wasmJsMain via Node.js)

try {
    module.exports = require('./build/Release/winapi_native.node');
} catch (err) {
    try {
        module.exports = require('./build/Debug/winapi_native.node');
    } catch (err2) {
        throw new Error(
            'Could not load winapi_native addon. ' +
            'Run `npm install` or `node-gyp rebuild` in src/js/node-winapi/dist directory.\n' +
            'Original error: ' + err.message + '\n' +
            'Debug error: ' + err2.message
        );
    }
}
