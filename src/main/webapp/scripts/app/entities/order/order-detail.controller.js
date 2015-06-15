'use strict';

angular.module('dryhomeApp')
    .controller('OrderDetailController', function ($scope, $stateParams, Order, Customer) {
        $scope.order = {};
        $scope.load = function (id) {
            Order.get({id: id}, function(result) {
              $scope.order = result;
            });
        };
        $scope.load($stateParams.id);
    });
