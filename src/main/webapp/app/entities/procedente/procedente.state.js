(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('procedente', {
            parent: 'entity',
            url: '/procedente?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Procedentes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/procedente/procedentes.html',
                    controller: 'ProcedenteController',
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
        .state('procedente-detail', {
            parent: 'procedente',
            url: '/procedente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Procedente'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/procedente/procedente-detail.html',
                    controller: 'ProcedenteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Procedente', function($stateParams, Procedente) {
                    return Procedente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'procedente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('procedente-detail.edit', {
            parent: 'procedente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedente/procedente-dialog.html',
                    controller: 'ProcedenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Procedente', function(Procedente) {
                            return Procedente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('procedente.new', {
            parent: 'procedente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedente/procedente-dialog.html',
                    controller: 'ProcedenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                acta: null,
                                acuerdo: null,
                                notificacion: null,
                                status_completado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('procedente', null, { reload: 'procedente' });
                }, function() {
                    $state.go('procedente');
                });
            }]
        })
        .state('procedente.edit', {
            parent: 'procedente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedente/procedente-dialog.html',
                    controller: 'ProcedenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Procedente', function(Procedente) {
                            return Procedente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('procedente', null, { reload: 'procedente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('procedente.delete', {
            parent: 'procedente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedente/procedente-delete-dialog.html',
                    controller: 'ProcedenteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Procedente', function(Procedente) {
                            return Procedente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('procedente', null, { reload: 'procedente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
