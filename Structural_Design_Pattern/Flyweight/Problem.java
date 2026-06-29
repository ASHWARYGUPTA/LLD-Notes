
import java.util.*;
//Problem ?? -> in Forest the x , y only change
//The other things of tree stays the same

class Tree {

    private int x;
    private int y;
    private String name;
    private String color;
    private String textures;

    public Tree(int x, int y, String name, String color, String textures) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.name = name;
        this.textures = textures;
    }

    public void draw() {
        System.out.println("Drawing Tree at ( " + x + "." + y + ") with type " + name);
    }
}

class Forest {

    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String textures) {
        Tree tree = new Tree(x, y, name, color, textures);
        trees.add(tree);
    }

    public void draw() {
        for (Tree tree : trees) {
            tree.draw();
        }
    }
}

public class Problem {

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
