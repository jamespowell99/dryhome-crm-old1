'use strict';

angular.module('dryhomeApp')
    .controller('CustomerController', function ($scope, Customer, ParseLinks, $http) {
        $scope.customers = [];
        $scope.page = 1;
        $scope.searchTerm = '';
        $scope.loadAll = function() {
            Customer.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.customers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            if($scope.searchTerm) {
                //TODO probably a better way for these params
                $http.get('/api/customers/search?companyName=' + $scope.searchTerm + '&page= ' + $scope.page + '&per_page=20').success(function (data, status, headers, config) {
                    $scope.customers = data
                    $scope.links = ParseLinks.parse(headers('link'));
                })
            } else {
                $scope.loadAll()
            }
        };
        $scope.loadAll();

        $scope.create = function () {
            Customer.update($scope.customer,
                function () {
                    $scope.loadAll();
                    $('#saveCustomerModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
                $('#saveCustomerModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
                $('#deleteCustomerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Customer.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCustomerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.customer = {name: null, contactTitle: null, contactFirst: null, contactSurname: null, tel: null, mob: null, email: null, address1: null, address2: null, address3: null, town: null, postCode: null, products: null, interested: null, paid: null, notes: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };


        $scope.$watch('searchTerm', function() {
            $scope.loadPage(1)
        });
    });
