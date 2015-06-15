'use strict';

angular.module('dryhomeApp')
    .factory('Order', function ($resource) {
        return $resource('api/orders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    var orderDateFrom = data.orderDate.split("-");
                    data.orderDate = new Date(new Date(orderDateFrom[0], orderDateFrom[1] - 1, orderDateFrom[2]));
                    if (data.dispatchDate) {
                        var dispatchDateFrom = data.dispatchDate.split("-");
                        data.dispatchDate = new Date(new Date(dispatchDateFrom[0], dispatchDateFrom[1] - 1, dispatchDateFrom[2]));
                    }
                    if (data.invoiceDate) {
                        var invoiceDateFrom = data.invoiceDate.split("-");
                        data.invoiceDate = new Date(new Date(invoiceDateFrom[0], invoiceDateFrom[1] - 1, invoiceDateFrom[2]));
                    }
                    if (data.paymentDate) {
                        var paymentDateFrom = data.paymentDate.split("-");
                        data.paymentDate = new Date(new Date(paymentDateFrom[0], paymentDateFrom[1] - 1, paymentDateFrom[2]));
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
