
module.exports = function(grunt) {

  // Project configuration
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    clean: {
      build: [ 'build' ]
    },
    jshint: {
      work: {
        files: ['src/*.js', 'Gruntfile.js']
      }
    },
    bootlint: {
      files: ['src/*.html']
    },
    uglify: {
      options: {
        banner: '/* <% pkg.name %> <% grunt.template.today("yyy-mm-dd") %> */\n'
      },
      files: {
        src: 'src/*.js',
        dest: 'build/',
        ext: 'min.js'
      }
    },
    watch: {
      js: {
        files: 'src/*.js',
        tasks: [ 'jshint', 'uglify' ]
      },
      html: {
        files: 'src/*.html',
        tasks: [ 'bootlint' ]
      }
    }
  });

  // load plugins
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-bootlint');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-watch');

  // Default tasks
  grunt.registerTask('default', ['clean:build']);
  grunt.registerTask('default', ['jshint:work']);
  grunt.registerTask('default', ['bootlint']);
  grunt.registerTask('default', ['uglify']);
  //grunt.registerTask('default', ['watch']);

};
