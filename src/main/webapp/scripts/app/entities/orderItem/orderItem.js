'use strict';

angular.module('dryhomeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderItem', {
                parent: 'entity',
                url: '/orderItem',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'dryhomeApp.orderItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderItem/orderItems.html',
                        controller: 'OrderItemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderItem');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderItemDetail', {
                parent: 'entity',
                url: '/orderItem/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'dryhomeApp.orderItem.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderItem/orderItem-detail.html',
                        controller: 'OrderItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderItem');
                        return $translate.refresh();
                    }]
                }
            });
    });
