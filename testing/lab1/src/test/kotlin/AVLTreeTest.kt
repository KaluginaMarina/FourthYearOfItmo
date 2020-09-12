import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class AVLTreeTest {
    @Test
    fun testCreateTree(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 10)
        root = tree.insert(root, 20)
        root = tree.insert(root, 30)
        root = tree.insert(root, 40)
        root = tree.insert(root, 50)
        root = tree.insert(root, 25)

        assertEquals("10 20 25 30 40 50 ", tree.preOrder(root), "Ошибка при создании дерева")
    }

    @Test
    fun testTreeWithOneNode() {
        val tree = AVLTree()
        var root: AVLTree.Node? = null
        root = tree.insert(root, 10)
        assertEquals("10 ", tree.preOrder(root), "Ошибка при создании дерева с одной вершиной")
    }

    @Test
    fun testTreeWithManyNode(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null
        var result = ""
        for (i in 1..100) {
            root = tree.insert(root, i)
            result = result.plus(i).plus(" ")
        }
        assertEquals(result, tree.preOrder(root), "Ошибка при создании дерева с большим количеством листов")
    }
}