package tn.esprit.interfaces;

import tn.esprit.models.Terrain;

import java.util.List;
import java.util.Set;

public interface ITerrain_service {

    public List<Terrain> myProprieties(int owner_id);
    public Terrain find_terrain(String name, String city, String country);

    public Terrain find_terrain_update(int id_terrain, String name, String city, String country);

    public List<Terrain> filterTerrain(String location, String sport_type, String rent_price);

    public List<Terrain> searchTerrain(String search_term);

    public void add_autoCompleteWord(String word);

    public Set<String> get_autoCompleteWords();
    void addEntity(Terrain t);
}


