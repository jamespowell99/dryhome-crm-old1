'use strict';

angular.module('dryhomeApp')
    .controller('ProductController', function ($scope, Product) {
        $scope.products = [];
        $scope.loadAll = function() {
            Product.query(function(result) {
               $scope.products = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Product.update($scope.product,
                function () {
                    $scope.loadAll();
                    $('#saveProductModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
                $('#saveProductModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
                $('#deleteProductConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Product.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProductConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.product = {name: null, description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
