# BUDGET APP:


##MODELS
User:
    String id
    String username
    String password
    String emailAddress 
    List expenses

Expense:
    Date dateTime
    String title
    String description 
    Double amount
    Category category 
    String importId

Category:
    Enum mainCategory
    Enum subcategory



##ENUMERATIONS
MainCategory:
   .... 

SubCategory:
   .... 



##PAGES
/login:
    login form

/dashboard:
    /listExpences
    /addExpence
    /statistics
    /importExpenses
    display total expenses this month

/listExpences:
    list of expenses with CRUD functionality
    sort functionality for list (date /amount / category / subcategory) 
    search functionality (all fields / range on amount) 
    pagination 

/addExpence
    add expence form

/statistics
    graph, x-time, y-amount, colour category
    selectable main -> subcategory
    selectable day / week / month /year /years
    pagination 

/importExpenses
    configure and save setup of order of fields to be imported from file
    select location / file field
    preview result
    save expenses



##NOTES
