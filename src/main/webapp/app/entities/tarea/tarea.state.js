(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tarea', {
            parent: 'entity',
            url: '/tarea?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kukulkanApp.tarea.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tarea/tareas.html',
                    controller: 'TareaController',
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tarea');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tarea-detail', {
            parent: 'tarea',
            url: '/tarea/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kukulkanApp.tarea.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tarea/tarea-detail.html',
                    controller: 'TareaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tarea');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tarea', function($stateParams, Tarea) {
                    return Tarea.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tarea',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tarea-detail.edit', {
            parent: 'tarea-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarea/tarea-dialog.html',
                    controller: 'TareaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tarea', function(Tarea) {
                            return Tarea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tarea.new', {
            parent: 'tarea',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarea/tarea-dialog.html',
                    controller: 'TareaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tarea', null, { reload: 'tarea' });
                }, function() {
                    $state.go('tarea');
                });
            }]
        })
        .state('tarea.edit', {
            parent: 'tarea',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarea/tarea-dialog.html',
                    controller: 'TareaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tarea', function(Tarea) {
                            return Tarea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tarea', null, { reload: 'tarea' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tarea.delete', {
            parent: 'tarea',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarea/tarea-delete-dialog.html',
                    controller: 'TareaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tarea', function(Tarea) {
                            return Tarea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tarea', null, { reload: 'tarea' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
