'use strict';

angular.module('dryhomeApp')
    .controller('OrderController', function ($scope, Order, Customer, ParseLinks, Product) {
        $scope.orders = [];
        $scope.customers = Customer.query();
        $scope.page = 1;
        $scope.products = Product.query();
        $scope.loadAll = function() {
            Order.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orders = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            console.log("creating order...")// + JSON.stringify($scope.order))
            Order.update($scope.order,
                function () {
                    $scope.loadAll();
                    $('#saveOrderModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.removeOrderItem = function(id ) {
            var indexToRemove = $scope.order.items.map(function(e) { return e.id; }).indexOf(id);
            $scope.order.items.splice( indexToRemove, 1 );
        }

        $scope.addOrderItem = function() {
            $scope.order.items.push({id: null})
        }

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
                    $scope.loadAll();
                    $('#deleteOrderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.order = {orderNumber: null, orderDate: null, dispatchDate: null, invoiceDate: null, placedBy: null, method: null, invoiceNumber: null, invoiceNotes1: null, invoiceNotes2: null, notes: null, paymentDate: null, paymentStatus: null, paymentType: null, paymentAmount: null, vatRate: null, id: null, items: [{id: null}]};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
