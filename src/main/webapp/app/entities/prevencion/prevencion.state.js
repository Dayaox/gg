(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('prevencion', {
            parent: 'entity',
            url: '/prevencion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Prevencions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prevencion/prevencions.html',
                    controller: 'PrevencionController',
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
        .state('prevencion-detail', {
            parent: 'prevencion',
            url: '/prevencion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Prevencion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prevencion/prevencion-detail.html',
                    controller: 'PrevencionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Prevencion', function($stateParams, Prevencion) {
                    return Prevencion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'prevencion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('prevencion-detail.edit', {
            parent: 'prevencion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prevencion/prevencion-dialog.html',
                    controller: 'PrevencionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Prevencion', function(Prevencion) {
                            return Prevencion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prevencion.new', {
            parent: 'prevencion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prevencion/prevencion-dialog.html',
                    controller: 'PrevencionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                oficio: null,
                                notificacion: null,
                                respuesta: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('prevencion', null, { reload: 'prevencion' });
                }, function() {
                    $state.go('prevencion');
                });
            }]
        })
        .state('prevencion.edit', {
            parent: 'prevencion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prevencion/prevencion-dialog.html',
                    controller: 'PrevencionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Prevencion', function(Prevencion) {
                            return Prevencion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prevencion', null, { reload: 'prevencion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prevencion.delete', {
            parent: 'prevencion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prevencion/prevencion-delete-dialog.html',
                    controller: 'PrevencionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Prevencion', function(Prevencion) {
                            return Prevencion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prevencion', null, { reload: 'prevencion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
