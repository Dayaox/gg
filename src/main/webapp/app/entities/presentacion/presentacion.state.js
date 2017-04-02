(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('presentacion', {
            parent: 'entity',
            url: '/presentacion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Presentacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/presentacion/presentacions.html',
                    controller: 'PresentacionController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('presentacion-detail', {
            parent: 'presentacion',
            url: '/presentacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Presentacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/presentacion/presentacion-detail.html',
                    controller: 'PresentacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Presentacion', function($stateParams, Presentacion) {
                    return Presentacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'presentacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('presentacion-detail.edit', {
            parent: 'presentacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/presentacion/presentacion-dialog.html',
                    controller: 'PresentacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Presentacion', function(Presentacion) {
                            return Presentacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('presentacion.new', {
            parent: 'presentacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/presentacion/presentacion-dialog.html',
                    controller: 'PresentacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                acuerdo: null,
                                notificacion: null,
                                status_completado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('presentacion', null, { reload: 'presentacion' });
                }, function() {
                    $state.go('presentacion');
                });
            }]
        })
        .state('presentacion.edit', {
            parent: 'presentacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/presentacion/presentacion-dialog.html',
                    controller: 'PresentacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Presentacion', function(Presentacion) {
                            return Presentacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('presentacion', null, { reload: 'presentacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('presentacion.delete', {
            parent: 'presentacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/presentacion/presentacion-delete-dialog.html',
                    controller: 'PresentacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Presentacion', function(Presentacion) {
                            return Presentacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('presentacion', null, { reload: 'presentacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
