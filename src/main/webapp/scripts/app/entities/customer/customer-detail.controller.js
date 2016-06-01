'use strict';

angular.module('dryhomeApp')
    .controller('CustomerDetailController', function ($scope, $stateParams, Customer, $http, Order) {
        $scope.customer = {};
        $scope.customerOrders = {};
        $scope.load = function (id) {
            Customer.get({id: id}, function(result) {
              $scope.customer = result;
            });
            $http.get("/api/customers/" + id + "/orders").success(
                function(response) {
                    $scope.customerOrders = response
                }
            );
        };
        $scope.load($stateParams.id);

        $scope.update = function (id) {
            Order.get({id: id}, function(result) {
                $scope.order = result;
                $('#saveOrderModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Order.get({id: id}, function(result) {
                $scope.order = result;
                $('#deleteOrderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Order.delete({id: id},
                function () {
                    $scope.load($stateParams.id);
                    $('#deleteOrderConfirmation').modal('hide');
                    //TODO pristine & untouched?
                });
        };
    });
