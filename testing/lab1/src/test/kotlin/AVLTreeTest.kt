import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class AVLTreeTest {
    @Test
    fun testCreateTree(){
        val tree = AVLTree()

        tree.root = tree.insert(tree.root, 10)
        tree.root = tree.insert(tree.root, 20)
        tree.root = tree.insert(tree.root, 30)
        tree.root = tree.insert(tree.root, 40)
        tree.root = tree.insert(tree.root, 50)
        tree.root = tree.insert(tree.root, 25)

        assertEquals("30 20 10 25 40 50 ", tree.preOrder(tree.root), "Ошибка при создании дерева")
    }
}