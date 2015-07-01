'use strict';

angular.module('dryhomeApp')
    .factory('Order', function ($resource) {
        return $resource('api/orders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.orderDate = new Date(data.orderDate);
                    if (data.dispatchDate) {
                        data.dispatchDate = new Date(data.dispatchDate);
                    }
                    if (data.invoiceDate) {
                        data.invoiceDate = new Date(data.invoiceDate);
                    }
                    if (data.paymentDate) {
                        data.paymentDate = new Date(data.paymentDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
