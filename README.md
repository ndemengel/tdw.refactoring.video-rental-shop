# Test Driven Workshop
## Refactoring: Video Rental Shop

You've just been hired to implement some small evolutions for the software of a modest video rental shop. The client - the owner of the store - is globally satisfied of the software - as she says, her needs are "*quite simples and* [she] *doesn't know much about softwares anyway*" - but she needs some adjustments to reflect changes in her activity.
She also sees it as an opportunity to fix various bugs, some of which being of particular annoyance in the day-to-day activity of the store.

Here are the points she expects to be addressed, ordered by business value:

1. The "Late returns" checkbox of the "Rented Movies" screen doesn't work at all. It is annoying because I have to scan all dates with my eyes to find which customer is late.
2. Because of the bug mentioned previously many customers have taken the habit to return movies late, so I resolved to charge them with additonal fees. To effectively continue applying this policy, I need the sytem to compute those fees and add them to the invoices produced when returning movies.
3. In order to propose discounts for special events (Xmas, Valentine's Day, etc...), I need prices to be parametrable depending on the genre of movies. Also, I would like to be able to change the periods that currently define the different prices. Generally speaking, I want the liberty to completely redefine the pricing policy. *(After some discussion regarding the cost and risk of developing such a configurable system - together with the difficulty for her to then change the rules - she agreed to only implement the rules she mentioned for now, and to then call you again if the need arises, providing that implementing new rules won't be to costly)*
4. Most of the time, the customer search doesn't work when using names. I would like it to be fixed, since it is painful to scan through the whole list of customers, and they do not always know their account number.
5. I would like to see the movie codes appear in the "Available Movies" screen, in order to know what to enter in the "Rent Movies" dialog, when my barcode reader is out of order. *(There might exist a better solution to address this need...)*


The client agreed to work iteratively, with frequent demonstrations, as she can't pay too much for the software and wants to be able to stop the project should anything go wrong. She also understood the point of evaluating the difficulty of each user story first, and to possibly re-prioritize the stories based on that evaluation.


Notes:
* There are many other points that would draw your attention (for instance: all movies must be returned at once, taxes are not handled, etc...). They only reflect the fact that this application is a sample one, and you are not expected to work on those points.
* To keep the sample application lightweight and focused, the database has been faked (see package xxx). You should not modify it, but you may use the static methods provided by the FakeXxxTable classes. Those methods represents SQL statements sent to the database.


## Instructions

The point of this exercice being to apply "clean code" principles and to put Test Driven Development into practice, there are some rules to follow:

* Never change code without a test.
* Never declare your current task to be done without having refactored your solution so that it is "clean enough".
* Commit frequently: each commit should bring an improvement or feature, covered by tests. It goes without saying that all tests must pass.
* Apply the "boy scout rule": always leave the code cleaner than you found it.

That said, be careful not to rewrite so much that you are not delivering business value anymore.


## Help

### Tests

Some end-to-end tests are provided in the "-it" module to help you start in covering the application.


### Git

To commit your work:
	git commit -m "My comment"
To discard all changes since your last commit:
	git reset --hard
To discard all changes up to commit XXX:
	git reset --hard XXX