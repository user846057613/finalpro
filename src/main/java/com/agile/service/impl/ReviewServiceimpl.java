package com.agile.service.impl;

import com.agile.dao.ReviewDao;
import com.agile.pojo.Review;
import com.agile.pojo.User;
import com.agile.pojo.example.ReviewExample;
import com.agile.service.ReviewService;
import com.agile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceimpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao = null;

    @Autowired
    private UserService userService = null;

    @Override
    public void add(Review review) {
        reviewDao.insert(review);
    }

    @Override
    public void delete(int id) {
        reviewDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewDao.updateByPrimaryKey(review);
    }

    @Override
    public Review get(int id) {
        return reviewDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Review> listByProductId(Integer product_id) {
        ReviewExample example = new ReviewExample();
        example.or().andProduct_idEqualTo(product_id);
        example.setOrderByClause("id desc");
        List<Review> reviews = reviewDao.selectByExample(example);
        setUser(reviews);
        return reviews;
    }

    public void setUser(List<Review> reviews) {
        for (Review review : reviews) {
            setUser(review);
        }
    }

    public void setUser(Review review) {
        User user = userService.get(review.getUser_id());
        review.setUser(user);
    }

    @Override
    public int getCount(int product_id) {
        return listByProductId(product_id).size();
    }
}
