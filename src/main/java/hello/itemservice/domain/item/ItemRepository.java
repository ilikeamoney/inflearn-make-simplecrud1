package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static 사용, 아이템 저장소
    private static long sequence = 0L; // static

    public Item save(Item item) { // 아이템 저장
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) { // ID 조회
        return store.get(id);
    }

    public List<Item> findAll() { // 목록 전체 조회
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateItem) { // 아이템 업데이트
        Item findItem = findById(itemId);
        findItem.setItemName(updateItem.getItemName());
        findItem.setPrice(updateItem.getPrice());
        findItem.setQuantity(updateItem.getQuantity());
    }

    public void clearStore() { // 저장소 삭제
        store.clear();
    }



}
