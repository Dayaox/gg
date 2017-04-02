(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('improcedente', {
            parent: 'entity',
            url: '/improcedente?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Improcedentes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/improcedente/improcedentes.html',
                    controller: 'ImprocedenteController',
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
        .state('improcedente-detail', {
            parent: 'improcedente',
            url: '/improcedente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Improcedente'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/improcedente/improcedente-detail.html',
                    controller: 'ImprocedenteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Improcedente', function($stateParams, Improcedente) {
                    return Improcedente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'improcedente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('improcedente-detail.edit', {
            parent: 'improcedente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/improcedente/improcedente-dialog.html',
                    controller: 'ImprocedenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Improcedente', function(Improcedente) {
                            return Improcedente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('improcedente.new', {
            parent: 'improcedente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/improcedente/improcedente-dialog.html',
                    controller: 'ImprocedenteDialogController',
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
                    $state.go('improcedente', null, { reload: 'improcedente' });
                }, function() {
                    $state.go('improcedente');
                });
            }]
        })
        .state('improcedente.edit', {
            parent: 'improcedente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/improcedente/improcedente-dialog.html',
                    controller: 'ImprocedenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Improcedente', function(Improcedente) {
                            return Improcedente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('improcedente', null, { reload: 'improcedente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('improcedente.delete', {
            parent: 'improcedente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/improcedente/improcedente-delete-dialog.html',
                    controller: 'ImprocedenteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Improcedente', function(Improcedente) {
                            return Improcedente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('improcedente', null, { reload: 'improcedente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
