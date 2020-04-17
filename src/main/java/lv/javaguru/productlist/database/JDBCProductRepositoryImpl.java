package lv.javaguru.productlist.database;

import lv.javaguru.productlist.domain.Category;
import lv.javaguru.productlist.domain.Product;
import lv.javaguru.productlist.domain.ProductCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JDBCProductRepositoryImpl implements ProductRepository {

    @Value( "${jdbc.url}" )
    private String jdbcUrl;

    @Value( "${driverClass}" )
    private String driverClass;

    @Value( "${database.user.name}" )
    private String userName;

    @Value( "${database.user.password}" )
    private String password;


    protected Connection getConnection() {
        try{
            return DriverManager.getConnection(jdbcUrl, userName, password);
        } catch (SQLException e) {
            System.out.println("Exception while getting connection to database");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void closeConnection(Connection connection) {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Exception while closing connection to database");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addProduct(Product product) {
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "insert into PRODUCTS(product_id, product_name, product_description, product_price, product_discount, product_category, product_actual_price) values(default, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setBigDecimal(4, product.getDiscount());
            preparedStatement.setObject(5, product.getCategory());
            preparedStatement.setBigDecimal(6, product.getActualPrice());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                product.setId(rs.getInt(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute ProductDAOImpl.save()");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "select * from PRODUCTS";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("product_name"));
                product.setDescription(resultSet.getString("product_description"));
                product.setPrice(resultSet.getBigDecimal("product_price"));
                product.setDiscount(resultSet.getBigDecimal("product_discount"));
                product.setCategory(Category.valueOf(resultSet.getString("product_category")));
                product.setActualPrice(resultSet.getBigDecimal("product_actual_price"));
                products.add(product);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list ProductDAOImpl.getAll()");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
        return products;
    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public List<Product> getProductByCategory(ProductCategory category) {
        return null;
    }

    @Override
    public Optional<Product> findProductByName(String productName) {
        return Optional.empty();
    }
}