(function() {
    'use strict';
    angular
        .module('opleSioeApp')
        .factory('Peticion', Peticion);

    Peticion.$inject = ['$resource'];

    function Peticion ($resource) {
        var resourceUrl =  'api/peticions/:id';

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
