(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('anexo-evaluacion', {
            parent: 'entity',
            url: '/anexo-evaluacion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Anexo_evaluacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/anexo-evaluacion/anexo-evaluacions.html',
                    controller: 'Anexo_evaluacionController',
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
        .state('anexo-evaluacion-detail', {
            parent: 'anexo-evaluacion',
            url: '/anexo-evaluacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Anexo_evaluacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/anexo-evaluacion/anexo-evaluacion-detail.html',
                    controller: 'Anexo_evaluacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Anexo_evaluacion', function($stateParams, Anexo_evaluacion) {
                    return Anexo_evaluacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'anexo-evaluacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('anexo-evaluacion-detail.edit', {
            parent: 'anexo-evaluacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo-evaluacion/anexo-evaluacion-dialog.html',
                    controller: 'Anexo_evaluacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Anexo_evaluacion', function(Anexo_evaluacion) {
                            return Anexo_evaluacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('anexo-evaluacion.new', {
            parent: 'anexo-evaluacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo-evaluacion/anexo-evaluacion-dialog.html',
                    controller: 'Anexo_evaluacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                archivo: null,
                                descripcion: null,
                                id_peticion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('anexo-evaluacion', null, { reload: 'anexo-evaluacion' });
                }, function() {
                    $state.go('anexo-evaluacion');
                });
            }]
        })
        .state('anexo-evaluacion.edit', {
            parent: 'anexo-evaluacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo-evaluacion/anexo-evaluacion-dialog.html',
                    controller: 'Anexo_evaluacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Anexo_evaluacion', function(Anexo_evaluacion) {
                            return Anexo_evaluacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('anexo-evaluacion', null, { reload: 'anexo-evaluacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('anexo-evaluacion.delete', {
            parent: 'anexo-evaluacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo-evaluacion/anexo-evaluacion-delete-dialog.html',
                    controller: 'Anexo_evaluacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Anexo_evaluacion', function(Anexo_evaluacion) {
                            return Anexo_evaluacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('anexo-evaluacion', null, { reload: 'anexo-evaluacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
