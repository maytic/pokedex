package com.maytic.pokedex.Pokemon;

/**
 * Class is used for getting color codes for dynamically created views that require a color
 */
public class PokemonServices {

    private PokemonServices(){

    }

    /**
     * function returns the color of a pokemon type such as water or fire
     * @param type
     * @return
     */
    public static String getTypeColor(String type){
        switch (type){
            case "Water":
                return "#445E9C";

            case "Grass":
                return "#4E8234";

            case "Electric":
                return "#A1871F";

            case "Psychic":
                return "#F85888";

            case "Ice":
                return "#98D8D8";

            case "Dragon":
                return "#7038F8";

            case "Dark":
                return "#705848";

            case "Fairy":
                return "#EE99AC";

            case "Fire":
                return "#F08030";

            case "Normal":
                return "#A8A878";

            case "Fighting":
                return "#C03028";

            case "Flying":
                return "#A890F0";

            case "Poison":
                return "#A040A0";

            case "Ground":
                return "#E0C068";

            case "Rock":
                return "#B8A038";

            case "Bug":
                return "#A8B820";

            case "Ghost":
                return "#705898";

            case "Steel":
                return "#B8B8D0";

            default:
                return "#FFFFFF";

        }

    }

    /**
     * function returns the color of a stat such as hp stat is red
     * @param stat
     * @return
     */
    public static String getStatColor(String stat){
        switch (stat){
            case "hp":
                return "#eb4034";
            case "attack":
                return "#e0aa70";
            case "defense":
                return "#3ee695";
            case "sp-atk":
                return "#e6e229";
            case "sp-def":
                return "#89e0b6";
            case "speed":
                return "#7bd3e3";
            default:
                return "#e089e0";
        }
    }


}
