package mvc.itemservice.domain.item;

public enum ItemType {

    BOOK("책"), FOOD("식품"), ETC("기타");

    private final String description;

    // 생성자
    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
