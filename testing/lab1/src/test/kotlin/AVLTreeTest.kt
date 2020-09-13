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

    @Test
    fun testDeletingNode(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 10)
        root = tree.insert(root, 20)
        root = tree.insert(root, 30)
        root = tree.insert(root, 40)
        root = tree.insert(root, 50)
        root = tree.insert(root, 25)
        root = tree.deleteNode(root, 25)

        assertEquals("10 20 30 40 50 ", tree.preOrder(root), "Ошибка при удалении листа")
    }

    @Test
    fun testDelitingAllNode(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 10)
        root = tree.insert(root, 20)
        root = tree.insert(root, 30)
        root = tree.insert(root, 40)
        root = tree.insert(root, 50)
        root = tree.insert(root, 25)
        root = tree.deleteNode(root, 25)
        root = tree.deleteNode(root, 50)
        root = tree.deleteNode(root, 40)
        root = tree.deleteNode(root, 30)
        root = tree.deleteNode(root, 20)
        root = tree.deleteNode(root, 10)

        assertEquals("", tree.preOrder(root), "Ошибка при удалении всех листьев")
    }

    @Test
    fun testDeletingTheHigherNode(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 10)
        root = tree.insert(root, 20)
        root = tree.insert(root, 30)
        root = tree.insert(root, 40)
        root = tree.insert(root, 50)
        root = tree.insert(root, 25)
        root = tree.deleteNode(root, 50)

        assertEquals("10 20 25 30 40 ", tree.preOrder(root), "Ошибка при удалении наибольшего листа")
    }

    @Test
    fun testDeletingTheSmallestNode(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 10)
        root = tree.insert(root, 20)
        root = tree.insert(root, 30)
        root = tree.insert(root, 40)
        root = tree.insert(root, 50)
        root = tree.insert(root, 25)
        root = tree.deleteNode(root, 10)

        assertEquals("20 25 30 40 50 ", tree.preOrder(root), "Ошибка при удалении наименьшего листа")
    }

    @Test
    fun testDeletingASingleNode(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 10)
        root = tree.deleteNode(root, 10)

        assertEquals("", tree.preOrder(root), "Ошибка при удалении единственного листа")
    }

    @Test
    fun testDeletingANonexistentNode(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 10)
        root = tree.insert(root, 20)
        root = tree.insert(root, 30)
        root = tree.insert(root, 40)
        root = tree.insert(root, 50)
        root = tree.insert(root, 25)
        root = tree.deleteNode(root, 100)

        assertEquals("10 20 25 30 40 50 ", tree.preOrder(root), "Ошибка при удалении единственного листа")
    }

    @Test
    fun testLeftRotate(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 20)
        root = tree.insert(root, 30)
        root = tree.insert(root, 40)

        assertEquals(2, tree.height(root), "Ошибка при балансировке - левый поворот")
    }

    @Test
    fun testRightRotate(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 50)
        root = tree.insert(root, 40)
        root = tree.insert(root, 30)

        assertEquals(2, tree.height(root), "Ошибка при балансировке - правый поворот")
    }

    @Test
    fun testLeftRotateWithChildren(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 0)
        root = tree.insert(root, 10)
        root = tree.insert(root, 20)
        root = tree.insert(root, 30)
        root = tree.insert(root, 40)
        root = tree.insert(root, 40)
        root = tree.insert(root, 50)
        root = tree.insert(root, 60)
        root = tree.insert(root, 70)
        root = tree.insert(root, 80)
        root = tree.insert(root, 90)

        assertEquals(4, tree.height(root), "Ошибка при балансировке листьев с детьми - левый поворот")
    }

    @Test
    fun testRightRotateWithChildren(){
        val tree = AVLTree()
        var root: AVLTree.Node? = null

        root = tree.insert(root, 90)
        root = tree.insert(root, 80)
        root = tree.insert(root, 70)
        root = tree.insert(root, 60)
        root = tree.insert(root, 50)
        root = tree.insert(root, 40)
        root = tree.insert(root, 30)
        root = tree.insert(root, 20)
        root = tree.insert(root, 10)
        root = tree.insert(root, 0)

        assertEquals(4, tree.height(root), "Ошибка при балансировке листьев с детьми - правый поворот")
    }
}