(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('evaluacion', {
            parent: 'entity',
            url: '/evaluacion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Evaluacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluacion/evaluacions.html',
                    controller: 'EvaluacionController',
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
        .state('evaluacion-detail', {
            parent: 'evaluacion',
            url: '/evaluacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Evaluacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluacion/evaluacion-detail.html',
                    controller: 'EvaluacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Evaluacion', function($stateParams, Evaluacion) {
                    return Evaluacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'evaluacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('evaluacion-detail.edit', {
            parent: 'evaluacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluacion/evaluacion-dialog.html',
                    controller: 'EvaluacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Evaluacion', function(Evaluacion) {
                            return Evaluacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluacion.new', {
            parent: 'evaluacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluacion/evaluacion-dialog.html',
                    controller: 'EvaluacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status_evaluacion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('evaluacion', null, { reload: 'evaluacion' });
                }, function() {
                    $state.go('evaluacion');
                });
            }]
        })
        .state('evaluacion.edit', {
            parent: 'evaluacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluacion/evaluacion-dialog.html',
                    controller: 'EvaluacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Evaluacion', function(Evaluacion) {
                            return Evaluacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluacion', null, { reload: 'evaluacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluacion.delete', {
            parent: 'evaluacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluacion/evaluacion-delete-dialog.html',
                    controller: 'EvaluacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Evaluacion', function(Evaluacion) {
                            return Evaluacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluacion', null, { reload: 'evaluacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
