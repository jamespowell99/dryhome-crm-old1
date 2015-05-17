'use strict';

angular.module('dryhomeApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
