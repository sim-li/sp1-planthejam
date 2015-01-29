// Karma config for build process (requires other options)
var baseConfig = require('./karma.conf.js');
module.exports = function(config) {
    // Load base config
    baseConfig(config);
    // Override base config
    config.set({
        singleRun: true,
        autoWatch: false,
        logLevel: config.LOG_INFO,
        browsers: ['PhantomJS'],
        plugins: [
            'karma-jasmine',
            'karma-phantomjs-launcher'
        ]
    });
};