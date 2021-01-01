# pokemon-db


## Tech/framework used
<b>Built with</b>
- Springboot
- Springboot H2
- Springboot Web
- Java Amazon Correto

## API Reference
- https://pokemon-db-123.herokuapp.com/pokemon/{id}

    id = an int representing the pokemons nationaldex id number
    error 404 when no pokemon is found

- https://pokemon-db-123.herokuapp.com/pokemons?name={name}

    name = pokemons name, the pokemon database will search the sql database for 5 pokemon matching queried name 
    (used for user of the pokedex app when searhing for pokemon)
    error 404 when no pokemon is found
    
- https://pokemon-db-123.herokuapp.com/pokemos?name={name}

    name = pokemons name, retrieves the pokemons name by exact match
    (alternative to id search)
    (used for user of the pokedex app when searhing for pokemon)
    error 404 when no pokemon is found


- https://pokemon-db-123.herokuapp.com/pokemons/type/{type}

    type = name of type ex) Water, Fire, Grass etc 
    returns all the pokemons with matching type 
    error 404 when no pokemon is found
    
- http://pokemon-db-123.herokuapp.com/pokemon/count

  returns the total amount of pokemon in the database

## License
Copyright [2020] [Maytic]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
