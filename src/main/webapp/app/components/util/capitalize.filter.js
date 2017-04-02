(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .filter('capitalize', capitalize);

    function capitalize() {
        return capitalizeFilter;

        function capitalizeFilter (input) {
            if (input !== null) {
                input = input.toLowerCase();
                input = input.substring(0, 1).toUpperCase() + input.substring(1);
            }
            return input;
        }
    }
})();
