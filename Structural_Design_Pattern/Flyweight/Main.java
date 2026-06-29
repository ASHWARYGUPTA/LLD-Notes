
//The other things of tree stays the same
import java.util.*;

class TreeType {

    private String name;
    private String color;
    private String textures;

    public TreeType(String name, String color, String textures) {
        this.name = name;
        this.color = color;
        this.textures = textures;
    }

    public void draw(int x, int y) {
        System.out.println("Drawing " + name + " color " + color + " texture " + textures + " at x : " + x + " at y : " + y);
    }

}

//Reusing it
class TreeTypeFactory {

    private Map<String, TreeType> treeTypeMap = new HashMap<>();

    public static TreeType getTreeType(String name, String color, String texture) {
        String key = name + "-" + color + "-" + texture;
        if (!treeTypeMap.containsKey(key)) {
            treeTypeMap.put(key, new TreeType(name, color, texture))
        }
        return treeTypeMap.get(key);
    }
}

class Tree {

    private int x;
    private int y;
    private TreeType treeType;

    public Tree(int x, int y, TreeType treeType) {
        this.x = x;
        this.y = y;
        this.treeType = treeType;
    }

    public void draw() {
        treeType.draw(x, y);
    }
}

class Forest {

    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture) {
        TreeType tree = TreeTypeFactory.getTreeType(name, color, texture);
        trees.add(new Tree(x, y, tree));
    }
}

class Main {

    public static void main(String[] args) {
        Forest forest = new Forest();

        String[] names = {"Oak", "Pine", "Birch", "Maple"};
        String[] colors = {"Green", "Dark Green", "Light Green"};
        String[] textures = {
            "oak.png",
            "pine.png",
            "birch.png",
            "maple.png"
        };

        Random random = new Random();

        for (int i = 0; i < 1_000_000; i++) {
            int x = random.nextInt(10000);
            int y = random.nextInt(10000);

            int type = random.nextInt(names.length);

            forest.plantTree(
                    x,
                    y,
                    names[type],
                    colors[random.nextInt(colors.length)],
                    textures[type]
            );
        }

        System.out.println("Forest created with 1,000,000 trees.");

        // Uncomment to draw all trees (will print 1 million lines!)
        forest.draw();
    }
}
