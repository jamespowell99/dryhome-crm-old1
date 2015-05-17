'use strict';

angular.module('dryhomeApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


