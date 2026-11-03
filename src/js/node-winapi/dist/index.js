// Entry point for winapi N-API native module
// Shared between Kotlin/JS (nodeMain) and Kotlin/WASM (wasmJsMain via Node.js)

module.exports = require('./build/Release/winapi_native.node');
