(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('persona', {
            parent: 'entity',
            url: '/persona',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kukulkanApp.persona.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/persona/personas.html',
                    controller: 'PersonaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('persona');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('persona-detail', {
            parent: 'persona',
            url: '/persona/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kukulkanApp.persona.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/persona/persona-detail.html',
                    controller: 'PersonaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('persona');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Persona', function($stateParams, Persona) {
                    return Persona.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'persona',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('persona-detail.edit', {
            parent: 'persona-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/persona/persona-dialog.html',
                    controller: 'PersonaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Persona', function(Persona) {
                            return Persona.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('persona.new', {
            parent: 'persona',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/persona/persona-dialog.html',
                    controller: 'PersonaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                edad: null,
                                numeroCredencial: null,
                                sueldo: null,
                                impuesto: null,
                                impuestoDetalle: null,
                                activo: false,
                                fechaLocalDate: null,
                                fechaZoneDateTime: null,
                                imagen: null,
                                imagenContentType: null,
                                imagenAnyBlob: null,
                                imagenAnyBlobContentType: null,
                                imagenBlob: null,
                                imagenBlobContentType: null,
                                desc: null,
                                instante: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('persona', null, { reload: 'persona' });
                }, function() {
                    $state.go('persona');
                });
            }]
        })
        .state('persona.edit', {
            parent: 'persona',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/persona/persona-dialog.html',
                    controller: 'PersonaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Persona', function(Persona) {
                            return Persona.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('persona', null, { reload: 'persona' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('persona.delete', {
            parent: 'persona',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/persona/persona-delete-dialog.html',
                    controller: 'PersonaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Persona', function(Persona) {
                            return Persona.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('persona', null, { reload: 'persona' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
