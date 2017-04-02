(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('anexo-prevencion', {
            parent: 'entity',
            url: '/anexo-prevencion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Anexo_prevencions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/anexo-prevencion/anexo-prevencions.html',
                    controller: 'Anexo_prevencionController',
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
        .state('anexo-prevencion-detail', {
            parent: 'anexo-prevencion',
            url: '/anexo-prevencion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Anexo_prevencion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/anexo-prevencion/anexo-prevencion-detail.html',
                    controller: 'Anexo_prevencionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Anexo_prevencion', function($stateParams, Anexo_prevencion) {
                    return Anexo_prevencion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'anexo-prevencion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('anexo-prevencion-detail.edit', {
            parent: 'anexo-prevencion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo-prevencion/anexo-prevencion-dialog.html',
                    controller: 'Anexo_prevencionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Anexo_prevencion', function(Anexo_prevencion) {
                            return Anexo_prevencion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('anexo-prevencion.new', {
            parent: 'anexo-prevencion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo-prevencion/anexo-prevencion-dialog.html',
                    controller: 'Anexo_prevencionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                archivo: null,
                                descripcion: null,
                                id_solicitud: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('anexo-prevencion', null, { reload: 'anexo-prevencion' });
                }, function() {
                    $state.go('anexo-prevencion');
                });
            }]
        })
        .state('anexo-prevencion.edit', {
            parent: 'anexo-prevencion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo-prevencion/anexo-prevencion-dialog.html',
                    controller: 'Anexo_prevencionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Anexo_prevencion', function(Anexo_prevencion) {
                            return Anexo_prevencion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('anexo-prevencion', null, { reload: 'anexo-prevencion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('anexo-prevencion.delete', {
            parent: 'anexo-prevencion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo-prevencion/anexo-prevencion-delete-dialog.html',
                    controller: 'Anexo_prevencionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Anexo_prevencion', function(Anexo_prevencion) {
                            return Anexo_prevencion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('anexo-prevencion', null, { reload: 'anexo-prevencion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
