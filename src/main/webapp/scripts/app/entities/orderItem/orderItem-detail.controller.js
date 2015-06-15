'use strict';

angular.module('dryhomeApp')
    .controller('OrderItemDetailController', function ($scope, $stateParams, OrderItem, Order, Product) {
        $scope.orderItem = {};
        $scope.load = function (id) {
            OrderItem.get({id: id}, function(result) {
              $scope.orderItem = result;
            });
        };
        $scope.load($stateParams.id);
    });
