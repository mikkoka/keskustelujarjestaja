function textdrag

[concepts, ohje, xlName] = textdread;
f = figure ('Color','w',...
    'menubar','none',...
    'name','Is there any conceivable use for something like this?',...
    'numbertitle','off');

aH = axes('Xlim', [0 1],...
    'Ylim', [0 1],...
    'Position', [0 0 1 1],...
    'visible', 'off');

li1 = line ([0.33 0.33], [0.1 0.9]);
li2 = line ([0.66 0.66], [0.1 0.9]);
li3 = line ([0.1 0.33], [0.5 0.5]);
li4 = line ([0.66 0.9], [0.5 0.5]);
set ([li1 li2 li3 li4], 'Color', 'Black',...
    'LineStyle', '-.',...
    'linewidth', 1);

ohje = text ('Position', [0.5 0.95],...
    'HorizontalAlignment', 'center',...
    'string', ohje);

nofc = length (concepts);

for a = 1 : nofc
    t{a} = text ('Position', [0.5 0.9 - a/nofc*0.7],...
        'string', concepts{a},...
        'fontsize',14,...
        'HorizontalAlignment', 'center',...
        'ButtonDownFcn', @startDragFcn);
end
set (f, 'WindowButtonUpFcn', @stopDragFcn);

b = uicontrol ('style', 'pushbutton',...
    'position', [252 10 60 30],...
    'String', 'Valmis',...
    'callback', {@moveOn, t, xlName});

    function startDragFcn(varargin)
        set (f, 'WindowButtonMotionFcn', @draggingFcn);
        if isempty (get (f, 'Userdata'))
            pt = get (aH, 'CurrentPoint');
            for i = 1 : length (t);
                dist(i) = sse((get (t{i}, 'Position') - pt(1, :)));
            end
            [~,j] = min(dist);
            set (f, 'Userdata', j);
        end
    end

    function draggingFcn (varargin)
        pt = get (aH, 'CurrentPoint');
        j = get (f, 'Userdata');
        set (t{j}, 'Position', [pt(1) pt(3)]);
    end

    function stopDragFcn (varargin)
        set (f, 'WindowbuttonMotionFcn', '');
        set (f, 'Userdata', []);
    end

    function [concepts, ohje, xlName] = textdread
        inputfile = fopen ('textdrag.txt', 'r');
        textline = fgetl(inputfile);
        a = 0;
        
        while ischar (textline);
            if strncmpi (textline, 'Ohje', 4) == 1;
                textline = fgetl(inputfile);
                while ~isempty (textline);
                    a = a + 1;
                    ohje{a} = textline;
                    textline = fgetl(inputfile);
                end
            elseif strncmpi (textline, 'Excel', 5) == 1;
                textline = fgetl(inputfile);
                xlName = textline;
            elseif strncmpi (textline, 'Käsitteet', 9) == 1;
                textline = fgetl(inputfile);
                while ischar (textline) && ~isempty (textline)
                    a = a + 1;
                    concepts{a} = textline;
                    textline = fgetl(inputfile);
                end
            else
            end
            a = 0;
            textline = fgetl(inputfile);
        end
        fclose (inputfile);
    end

    function moveOn (varargin)
        t = varargin{3};
        xlName = varargin{4};
        b = 0; c = 0; d = 0; e = 0;
        for a = 1 : length (t)
            pos = get (t{a}, 'Position');
            if pos(1) < 0.33 && pos(2) > 0.5
                b =  b + 1;
                data{b, 1} = get (t{a}, 'String');
            elseif pos(1) > 0.66 && pos(2) > 0.5
                c =  c + 1;
                data{c, 2} = get (t{a}, 'String');
            elseif pos(1) < 0.33 && pos(2) < 0.5
                d =  d + 1;
                data{d, 3} = get (t{a}, 'String');
            elseif pos(1) > 0.66 && pos(2) < 0.5
                e =  e + 1;
                data{e, 4} = get (t{a}, 'String');
            else
            end
        end
        xlswrite(xlName,data,1);
        winopen (xlName)
    end
end