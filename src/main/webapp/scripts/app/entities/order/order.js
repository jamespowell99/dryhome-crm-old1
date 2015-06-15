'use strict';

angular.module('dryhomeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('order', {
                parent: 'entity',
                url: '/order',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'dryhomeApp.order.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/order/orders.html',
                        controller: 'OrderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('order');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderDetail', {
                parent: 'entity',
                url: '/order/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'dryhomeApp.order.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/order/order-detail.html',
                        controller: 'OrderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('order');
                        return $translate.refresh();
                    }]
                }
            });
    });
