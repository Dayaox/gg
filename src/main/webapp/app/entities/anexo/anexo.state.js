(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('anexo', {
            parent: 'entity',
            url: '/anexo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Anexos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/anexo/anexos.html',
                    controller: 'AnexoController',
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
        .state('anexo-detail', {
            parent: 'anexo',
            url: '/anexo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Anexo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/anexo/anexo-detail.html',
                    controller: 'AnexoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Anexo', function($stateParams, Anexo) {
                    return Anexo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'anexo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('anexo-detail.edit', {
            parent: 'anexo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo/anexo-dialog.html',
                    controller: 'AnexoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Anexo', function(Anexo) {
                            return Anexo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('anexo.new', {
            parent: 'anexo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo/anexo-dialog.html',
                    controller: 'AnexoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                archivo: null,
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('anexo', null, { reload: 'anexo' });
                }, function() {
                    $state.go('anexo');
                });
            }]
        })
        .state('anexo.edit', {
            parent: 'anexo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo/anexo-dialog.html',
                    controller: 'AnexoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Anexo', function(Anexo) {
                            return Anexo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('anexo', null, { reload: 'anexo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('anexo.delete', {
            parent: 'anexo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/anexo/anexo-delete-dialog.html',
                    controller: 'AnexoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Anexo', function(Anexo) {
                            return Anexo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('anexo', null, { reload: 'anexo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
