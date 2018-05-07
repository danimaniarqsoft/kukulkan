(function() {
    'use strict';
    angular
        .module('kukulkanApp')
        .factory('Tarea', Tarea);

    Tarea.$inject = ['$resource'];

    function Tarea ($resource) {
        var resourceUrl =  'api/tareas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
