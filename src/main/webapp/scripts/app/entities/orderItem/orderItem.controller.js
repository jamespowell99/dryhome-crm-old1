'use strict';

angular.module('dryhomeApp')
    .controller('OrderItemController', function ($scope, OrderItem, Order, Product) {
        $scope.orderItems = [];
        $scope.orders = Order.query();
        $scope.products = Product.query();
        $scope.loadAll = function() {
            OrderItem.query(function(result) {
               $scope.orderItems = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            OrderItem.update($scope.orderItem,
                function () {
                    $scope.loadAll();
                    $('#saveOrderItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OrderItem.get({id: id}, function(result) {
                $scope.orderItem = result;
                $('#saveOrderItemModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OrderItem.get({id: id}, function(result) {
                $scope.orderItem = result;
                $('#deleteOrderItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.orderItem = {price: null, qty: null, notes: null, order: null, serialNumber: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
